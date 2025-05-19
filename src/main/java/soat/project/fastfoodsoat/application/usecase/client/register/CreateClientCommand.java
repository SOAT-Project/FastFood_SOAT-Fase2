package soat.project.fastfoodsoat.application.usecase.client.register;

public record CreateClientCommand(
        String name,
        String email,
        String cpf
){
    public static CreateClientCommand with(
            final String name,
            final String email,
            final String cpf
    ) {
        return new CreateClientCommand(
                name,
                email,
                cpf
        );
    }
}
