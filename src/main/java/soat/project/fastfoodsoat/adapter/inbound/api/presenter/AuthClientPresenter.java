package soat.project.fastfoodsoat.adapter.inbound.api.presenter;

import soat.project.fastfoodsoat.adapter.inbound.api.model.ClientAuthResponse;
import soat.project.fastfoodsoat.application.usecase.client.auth.AuthClientOutput;

public interface AuthClientPresenter {
    static ClientAuthResponse present(final AuthClientOutput output) {
        return new ClientAuthResponse(
                output.publicId(),
                output.name(),
                output.email(),
                output.cpf()
        );
    }
}
