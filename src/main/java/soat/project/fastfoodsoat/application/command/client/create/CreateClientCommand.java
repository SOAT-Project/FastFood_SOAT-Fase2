package soat.project.fastfoodsoat.application.command.client.create;

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
