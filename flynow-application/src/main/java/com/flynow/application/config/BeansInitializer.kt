package com.flynow.application.config

import com.flynow.infrastructure.config.startup.DataLoader
import com.flynow.infrastructure.mappers.CommentMapper
import com.flynow.infrastructure.mappers.CompanyMapper
import com.flynow.infrastructure.mappers.RoleMapper
import com.flynow.infrastructure.mappers.UserMapper
import com.flynow.infrastructure.repository.command.*
import com.flynow.infrastructure.repository.query.*
import com.flynow.infrastructure.services.TestService
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
    // ── Mappers (infrastructure) ───────────────────────────────────────────
    bean<CompanyMapper>()
    bean<CommentMapper>()
    bean<UserMapper>()
    bean<RoleMapper>()
    // AircraftTypeMapper.

    // ── Service implementations ─────────────────────────────────────────────
    bean<TestService>()
    bean<JwtService>()
    bean<AuthService>()
    bean<UserDetailsServiceImpl>()
    bean<CompanyServiceImpl>()
    bean<ImageService>()
    bean<OfferServiceImpl>()
    bean<CommentServiceImpl>()
    bean<UserServiceImpl>()

    // ── Data seeding  ───────────────────────────────────────────────────────
    bean<DataLoader>()

    // ── Command repositories ────────────────────────────────────────────────
    bean<CommentCommandRepositoryImpl>()
    bean<CompanyCommandRepositoryImpl>()
    bean<FlightCommandRepositoryImpl>()
    bean<FlightPictureCommandRepositoryImpl>()
    bean<UserCommandRepositoryImpl>()
    bean<UserFlightCommandRepositoryImpl>()

    // ── Query repositories ──────────────────────────────────────────────────
    bean<AircraftTypeQueryRepositoryImpl>()
    bean<CompanyQueryRepositoryImpl>()
    bean<FlightQueryRepositoryImpl>()
    bean<UserFlightQueryRepositoryImpl>()
    bean<UserQueryRepositoryImpl>()
}