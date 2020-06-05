package com.example.emergency.model

data class EmployeesData (val data:List<Employee>) {
}

data class Employee (val id:String,
                     val employee_name:String,
                     val employee_salary:String,
                     val employee_age:String,
                     val profile_image: String) {
}