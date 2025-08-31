package com.vipuljha.statik.core.data

abstract class BaseMapper<Dto, Model> {
    abstract fun toModel(dto: Dto): Model
    open fun toModelList(dtoList: List<Dto>): List<Model> = dtoList.map { toModel(it) }
    abstract fun toDto(model: Model): Dto
    open fun toDtoList(modelList: List<Model>): List<Dto> = modelList.map { toDto(it) }
}