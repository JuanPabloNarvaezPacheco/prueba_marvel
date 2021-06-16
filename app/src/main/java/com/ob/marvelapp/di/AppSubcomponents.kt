package com.ob.marvelapp.di

import dagger.Module

@Module(subcomponents = [HeroListSubComponent::class, HeroDetailSubComponent::class])
interface AppSubComponents