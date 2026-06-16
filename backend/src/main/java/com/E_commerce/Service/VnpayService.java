package com.E_commerce.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.E_commerce.Config.VnpayConfig;
import com.E_commerce.Entity.OrderEntity;
import com.E_commerce.Entity.PaymentEntity;
import com.E_commerce.Enum.OrderStatus;
import com.E_commerce.Enum.PaymentStatus;
import com.E_commerce.Exception.AppException;
import com.E_commerce.Exception.ErrorCode;
import com.E_commerce.Repository.OrderRepository;
import com.E_commerce.Repository.PaymentRepository;
import com.E_commerce.utils.VnpayUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
@Service
public class VnpayService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private VnpayConfig vnpayConfig;
    public String createPaymentUrl(Long orderId, String bankCode, String language, HttpServletRequest request) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        PaymentEntity payment = paymentRepository.findByOrderId(orderId).orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));
        long amount = order.getTotalAmount().longValue() * 100;
        Map<String, String> params = new HashMap<>();
        params.put("vnp_Version", "2.1.0");
        params.put("vnp_Command", "pay");
        params.put("vnp_TmnCode", vnpayConfig.getTmnCode());
        params.put("vnp_Amount", String.valueOf(amount));
        params.put("vnp_CurrCode", "VND");
        params.put("vnp_TxnRef", String.valueOf(payment.getId()));
        params.put("vnp_OrderInfo", "Thanh toan don hang: " + orderId);
        params.put("vnp_OrderType", "other");
        params.put("vnp_ReturnUrl", vnpayConfig.getReturnUrl());
        params.put("vnp_IpAddr", VnpayUtil.getIpAddress(request));
        if (bankCode != null && !bankCode.isEmpty()) {
            params.put("vnp_BankCode", bankCode);
        }
        params.put("vnp_Locale", (language != null && !language.isEmpty()) ? language : "vn");
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        params.put("vnp_CreateDate", formatter.format(cld.getTime()));
        cld.add(Calendar.MINUTE, 15);
        params.put("vnp_ExpireDate", formatter.format(cld.getTime()));
        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (int i = 0; i < fieldNames.size(); i++) {
            String fieldName = fieldNames.get(i);
            String fieldValue = params.get(fieldName);
            hashData.append(fieldName).append("=").append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
            query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII)).append("=").append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
            if (i < fieldNames.size() - 1) {
                hashData.append("&");
                query.append("&");
            }
        }
        String secureHash = VnpayUtil.hmacSHA512(
                vnpayConfig.getHashSecret(),
                hashData.toString()
        );
        return vnpayConfig.getPayUrl() + "?" + query + "&vnp_SecureHash=" + secureHash;
    }
    public String handleReturn(HttpServletRequest request) {

        Map<String, String> fields = new HashMap<>();

        request.getParameterMap().forEach((key, value) -> {
            if (value.length > 0) {
                fields.put(key, value[0]);
            }
        });
        String vnpSecureHash = fields.get("vnp_SecureHash");
        fields.remove("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        for (int i = 0; i < fieldNames.size(); i++) {
            String fieldName = fieldNames.get(i);
            String fieldValue = fields.get(fieldName);
            hashData.append(fieldName).append("=").append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
            if (i < fieldNames.size() - 1) {
                hashData.append("&");
            }
        }
        String signValue = VnpayUtil.hmacSHA512(
                vnpayConfig.getHashSecret(),
                hashData.toString()
        );
        if (!signValue.equals(vnpSecureHash)) {
            throw new AppException(ErrorCode.PAYMENT_SIGNATURE_INVALID);
        }
        String responseCode = request.getParameter("vnp_ResponseCode");
        String txnRef = request.getParameter("vnp_TxnRef");
        PaymentEntity payment = paymentRepository.findById(Long.parseLong(txnRef)).orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));
        if ("00".equals(responseCode)) {
            payment.setStatus(PaymentStatus.SUCCESS);
            OrderEntity order = payment.getOrder();
            order.setStatus(OrderStatus.PROCESSING);
            orderRepository.save(order);
            paymentRepository.save(payment);
            return "PAYMENT SUCCESS";
        }
        payment.setStatus(PaymentStatus.FAILED);
        paymentRepository.save(payment);
        throw new AppException(ErrorCode.PAYMENT_FAILED);
    }
    @Transactional
    public String createPayment(Long orderId, String bankCode, String language, HttpServletRequest request) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        PaymentEntity payment = paymentRepository.findByOrderId(orderId)
        .orElseGet(() -> {
            PaymentEntity p = new PaymentEntity();
            p.setOrder(order);
            p.setAmount(order.getTotalAmount());
            p.setStatus(PaymentStatus.PENDING);
            return paymentRepository.save(p);
        });
        return createPaymentUrl(orderId, bankCode, language, request);
    }
}
