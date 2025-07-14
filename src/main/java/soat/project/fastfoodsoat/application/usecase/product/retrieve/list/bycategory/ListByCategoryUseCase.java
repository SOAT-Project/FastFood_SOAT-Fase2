package soat.project.fastfoodsoat.application.usecase.product.retrieve.list.bycategory;

import soat.project.fastfoodsoat.application.usecase.UseCase;
import soat.project.fastfoodsoat.application.command.product.retrieve.list.bycategory.ListByCategoryCommand;
import soat.project.fastfoodsoat.application.output.product.ListByCategoryOutput;
import soat.project.fastfoodsoat.domain.pagination.Pagination;

public abstract class ListByCategoryUseCase
        extends UseCase<ListByCategoryCommand, Pagination<ListByCategoryOutput>> {
}
