package soat.project.fastfoodsoat.adapter.inbound.api.presenter;

import soat.project.fastfoodsoat.adapter.inbound.api.model.CreateClientResponse;
import soat.project.fastfoodsoat.application.usecase.client.register.CreateClientOutput;

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
