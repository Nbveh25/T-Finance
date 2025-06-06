package ru.practice.t_finance.domain.usecases

import androidx.compose.ui.graphics.Color
import jakarta.inject.Inject
import ru.practice.t_finance.domain.model.GetCategoryModel
import ru.practice.t_finance.domain.repository.CategoryRepository

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(): Result<List<GetCategoryModel>> {
        return repository.getCategories().map {
            it.map { category ->
                GetCategoryModel(
                    id = category.id,
                    name = category.name,
                    color = category.color.toColor(),
                    iconPath = category.iconPath,
                )
            }
        }
    }

    fun String.toColor(): Color {
        var colorString = this
        // Удаляем # если есть
        if (colorString.startsWith("#")) {
            colorString = colorString.substring(1)
        }
        // Добавляем альфа-канал если его нет (FF - полностью непрозрачный)
        if (colorString.length == 6) {
            colorString = "FF$colorString"
        }
        // Конвертируем в long и создаем Color
        return Color(colorString.toLong(16))
    }
}