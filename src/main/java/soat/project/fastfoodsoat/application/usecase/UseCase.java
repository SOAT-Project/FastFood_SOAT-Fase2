package soat.project.fastfoodsoat.application.usecase;

public abstract class UseCase<IN, OUT> {
    public abstract OUT execute(IN command);
}
