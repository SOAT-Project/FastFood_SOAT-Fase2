package soat.project.fastfoodsoat.infrastructure.web.presenter;

import soat.project.fastfoodsoat.infrastructure.web.model.response.client.CreateClientResponse;
import soat.project.fastfoodsoat.application.output.client.CreateClientOutput;

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
