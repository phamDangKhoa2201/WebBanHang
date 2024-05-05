import{
    IsString, IsNotEmpty, IsNumber, isDate, IsDate, IsPhoneNumber, isNotEmpty
}
from 'class-validator'
export class LoginDTO{
    @IsPhoneNumber()
    @IsNotEmpty()
    phone_number: string;

    @IsNotEmpty()
    @IsString()
    password: string;

    constructor(data:any){
        this.phone_number = data.phone_number;
        this.password = data.passWord;
    }
}