import { Component,ViewChild } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { FooterComponent } from "../footer/footer.component";
import { LoginDTO } from '../../dtos/user/login.dto';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import {LoginRespone} from '../../respones/user/login.respone';
import { TokenService } from '../../services/token.service';
import { RoleService } from '../../services/role.service';
import { Role } from '../../../models/role';
import { error } from 'console';

@Component({
    selector: 'app-login',
    standalone: true,
    templateUrl: './login.component.html',
    styleUrl: './login.component.scss',
    imports: [FormsModule,CommonModule]
})
export class LoginComponent {
    @ViewChild('loginForm') loginForm! : NgForm;
    phoneNumber:string ='44556677';
    password:string='123456';
    roles: Role[] = []; // Mảng roles
    rememberMe: boolean = true;
    selectedRole: Role | undefined;
    constructor( 
        private router:Router, 
        private userService: UserService,
        private tokenService: TokenService,
        private roleService: RoleService
        
        ){

    }
    onPhoneNumberChange(){
        console.log('Phone type: '+ this.phoneNumber)
    }
    ngOnInit() {
        // Gọi API lấy danh sách roles và lưu vào biến roles
        debugger
        this.roleService.getRoles().subscribe({      
          next: (roles: Role[]) => { // Sử dụng kiểu Role[]
            debugger
            this.roles = roles;
            this.selectedRole = roles.length > 0 ? roles[0] : undefined;
          },
          complete: () => {
            debugger
          },  
          error: (error: any) => {
            debugger
            console.error('Error getting roles:', error);
          }
        });
      }
    login(){
        const message = 'phone: '+this.phoneNumber 
                        +' password: '+this.password;
        //alert(message);
        
        const loginDTO:LoginDTO = {
            "phone_number": this.phoneNumber,
            "password": this.password
        };
        this.userService.login(loginDTO).subscribe({
            next:(response: LoginRespone) =>{
                debugger
                const {token} = response
                this.tokenService.setToken(token);
                //xử lí kết quả trả về khi đăng kí thành công
                //this.router.navigate(['/login']);
            },
            complete: () =>{
                debugger;
            },
            error:(error:any)=>{
                alert(error?.error?.message);
                
            }
        })
        
    }
}
