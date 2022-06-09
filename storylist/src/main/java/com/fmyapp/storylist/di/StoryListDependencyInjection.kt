package com.fmyapp.storylist.di

import com.fmyapp.storylist.data.repository.ItemRepositoryImpl
import com.fmyapp.storylist.data.source.ItemRemoteDataSource
import com.fmyapp.storylist.domain.repository.ItemRepository
import com.fmyapp.storylist.domain.usecase.ItemInteractor
import com.fmyapp.storylist.domain.usecase.ItemUseCases
import com.fmyapp.storylist.presentation.storylist.StoryListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module


fun injectStoryLIstKoinModules() = loadModules

private val loadModules by lazy {
    loadKoinModules(modules)
}

val dataSource: Module = module {
    single { ItemRemoteDataSource(apiService = get()) }
}

val repository: Module = module {
    single<ItemRepository> { ItemRepositoryImpl(remoteDataSource = get()) }
}

val useCases: Module = module {
    factory<ItemUseCases> { ItemInteractor(repository = get()) }
}

val viewModel: Module = module {
    viewModel { StoryListViewModel(useCase = get()) }
}

val modules = listOf(
    dataSource,
    repository,
    useCases,
    viewModel
)