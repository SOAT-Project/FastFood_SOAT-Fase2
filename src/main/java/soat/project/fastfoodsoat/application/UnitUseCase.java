package soat.project.fastfoodsoat.application;

public abstract class UnitUseCase<IN> {
    public abstract void execute(IN command);
}
