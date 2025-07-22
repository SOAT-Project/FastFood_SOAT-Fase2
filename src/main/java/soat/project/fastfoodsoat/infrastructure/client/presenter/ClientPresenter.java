package soat.project.fastfoodsoat.infrastructure.client.presenter;

import soat.project.fastfoodsoat.infrastructure.client.model.response.CreateClientResponse;
import soat.project.fastfoodsoat.application.output.client.create.CreateClientOutput;

public interface ClientPresenter {

    static CreateClientResponse present(final CreateClientOutput output) {
        return new CreateClientResponse(
                output.publicId(),
                output.name(),
                output.email(),
                output.CPF()
        );
    }

}
