package com.example.sswms.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterForm {

    @NotBlank(message = "メールアドレスを入力してください")
    @Size(max = 64, message = "64文字以内で入力してください")
    private String email;

    @NotBlank(message = "名前を入力してください")
    @Size(max = 60, message = "60文字以内で入力してください")
    private String name;


    @NotBlank(message = "パスワードを入力してください")
    @Size(max = 32, message = "32文字以内で入力してください")
    private String password;


}
