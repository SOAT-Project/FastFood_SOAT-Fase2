package soat.project.fastfoodsoat.application.usecase;

public abstract class UnitUseCase<IN> {
    public abstract void execute(IN command);
}
