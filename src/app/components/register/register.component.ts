import { Component, ViewChild } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { FooterComponent } from "../footer/footer.component";
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { response } from 'express';
import { error } from 'console';
import { UserService } from '../../services/user.service';
import { RegisterDTO } from '../../dtos/user/register.dto';


@Component({
    selector: 'app-register',
    standalone: true,
    templateUrl: './register.component.html',
    styleUrl: './register.component.scss',
    imports: [
        HeaderComponent, 
        FooterComponent,
        FormsModule,
        CommonModule,
    ]
})

export class RegisterComponent {
    @ViewChild('registerForm') registerForm! : NgForm;
    //khai báo các biến tương ứng với các trường dữ liệu trên form
    phoneNumber:string;
    passWord:string;
    retryPassword:string;
    fullName: string;
    dateOfBirth: Date;
    address: string;
    isAccepted:boolean;
    constructor( private router:Router, private userService: UserService){
        this.phoneNumber = '';
        this.passWord='';
        this.retryPassword='';
        this.fullName = '';
        this.dateOfBirth = new Date();
        this.dateOfBirth.setFullYear(this.dateOfBirth.getFullYear() -18);
        this.address = '';
        this.isAccepted =true;
        //inject

    }
    onPhoneNumberChange(){
        console.log('Phone type: '+ this.phoneNumber)
    }
    register(){
        const message = 'phone: '+this.phoneNumber 
                        +' password: '+this.passWord
                        +' retryPassword: '+this.retryPassword
                        +' fullName: '+this.fullName
                        +' address: '+this.address
                        +' DateOfBirth: '+this.dateOfBirth
                        +' isAccepted: '+this.isAccepted;
        //alert(message);
        
        const registerDTO:RegisterDTO = {
            "fullname": this.fullName,
            "phone_number": this.phoneNumber,
            "address": this.address,
            "password": this.passWord,
            "retype_password": this.retryPassword,
            "date_of_birth": this.dateOfBirth,
            "facebook_account_id": 0,
            "google_account_id": 0,
            "role_id":1
        };
        this.userService.register(registerDTO).subscribe({
            next:(response:any) =>{
                debugger
                //xử lí kết quả trả về khi đăng kí thành công
                this.router.navigate(['/login']);
            },
            complete: () =>{
                debugger;
            },
            error:(error:any)=>{
                alert('Cannot register, error: '+error.error);
                
            }
        })
        
    }
    //kiểm tra mật khẩu và nhập lại mật khẩu
    checkPassWordsMatch(){
        if(this.passWord !== this.retryPassword){
            this.registerForm.form.controls['retryPassword'].setErrors({'passwordMismatch':true});
        }
        else{
            this.registerForm.form.controls['retryPassword'].setErrors(null);
        }
    }
    checkAge(){
        if(this.dateOfBirth){
            const today = new Date()
            const birthDay = new Date(this.dateOfBirth);
            let age = today.getFullYear() - birthDay.getFullYear();
            const monthDiff = today.getMonth() - birthDay.getMonth();
            if(monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDay.getDate())){
                age--;
            }
            if(age < 12){
                this.registerForm.form.controls['dateOfBirth'].setErrors({'invalidAge':true});
            }
            else{
                this.registerForm.form.controls['dateOfBirth'].setErrors(null);
            }
        }
        
    }
    
}
