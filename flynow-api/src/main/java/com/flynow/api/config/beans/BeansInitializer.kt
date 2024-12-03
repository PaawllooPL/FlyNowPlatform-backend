package com.flynow.api.config.beans

import com.flynow.api.config.startup.DataLoader
import com.flynow.service.mappers.CommentMapper
import com.flynow.service.mappers.CompanyMapper
import com.flynow.service.mappers.RoleMapper
import com.flynow.service.mappers.UserMapper
import com.flynow.service.services.*
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans

class BeansInitializer: ApplicationContextInitializer<GenericApplicationContext> {
    override fun initialize(context: GenericApplicationContext) {
        beans.initialize(context)
    }
}


val beans = beans {
    bean<CompanyMapper>()
    bean<CommentMapper>()
    bean<TestService>()
    bean<UserMapper>()
    bean<RoleMapper>()
    bean<JwtService>()
    bean<AuthService>()
    bean<UserDetailsServiceImpl>()
    bean<CompanyServiceImpl>()
    bean<ImageService>()
    bean<DataLoader>()
    bean<OfferServiceImpl>()
    bean<CommentServiceImpl>()
    bean<UserServiceImpl>()
}