package com.E_commerce.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USER_NOT_FOUND(1001, "Không tìm thấy người dùng"),
    SEND_EMAIL_FAILED(1002, "Gửi email thất bại"),
    OTP_EXPIRED(1003, "Mã OTP đã hết hạn"),
    OTP_INVALID(1004, "Mã OTP không hợp lệ"),
    UNAUTHENTICATED(1005, "Sai tài khoản hoặc mật khẩu"),
    TOKEN_GENERATION_FAILED(1006, "Không thể tạo token đăng nhập"),
    USER_NOT_ACTIVE(1007, "Tài khoản không thể đăng nhập"),
    EMAIL_EXISTED(1008, "Email đã được sử dụng"),
    USERNAME_EXISTED(1009, "Tên đăng nhập đã tồn tại"),
    USERNAME_NOT_CHANGED(1010, "Tên đăng nhập mới phải khác tên hiện tại"),
    INVALID_STATE(1011, "Không thể thực hiện thao tác do trạng thái hiện tại tài khoản"),
    UNAUTHORIZED(1012, "Bạn không có quyền thực hiện hành động này"),
    ADDRESS_NOT_FOUND(2001, "Không tìm thấy địa chỉ"),
    ADDRESS_ALREADY_DELETED(2002, "Địa chỉ này đã bị xoá trước đó"),
    CATEGORY_NOT_FOUND(3001,"Không tìm thấy danh mục"),
    PRODUCT_NOT_FOUND(4001, "Sản phẩm không tồn tại"),
    PRODUCT_ALREADY_DELETED(4002,"Sản phẩm đã bị xoá"),
    PRODUCT_MEDIA_NOT_FOUND(4003, "Media sản phẩm không tồn tại"),
    MEDIA_NOT_ACTIVE(4004, "Media không ở trạng thái hoạt động");
    private int code;
    private String message;
}
