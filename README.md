# service-starter-dotnetcore
This is a sample starter services incorporating [best practices](https://github.com/cd-jump-start/service-starter/blob/master/best-practices) for writing services in spring-boot.
Uses PostgreSQL database.


This repo contains the following:  
-PaymentApi - the sample service with basic payment flow for demo purposes. It exposes a payment REST API 
that receives a payment request and performs actions to complete a payment transaction. 
It has a database that holds the payments info in a Payment table. It also has a BankInfo table that holds basic info on 
a Bank and the URLs it provides for validating accounts and making payments. 

The PaymentApi takes connectionString from externalised configuration - can be specified via external file, command line or environment variables 

