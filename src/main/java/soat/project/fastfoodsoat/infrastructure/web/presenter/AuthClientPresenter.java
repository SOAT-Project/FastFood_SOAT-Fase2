package soat.project.fastfoodsoat.infrastructure.web.presenter;

import soat.project.fastfoodsoat.infrastructure.web.model.response.client.ClientAuthResponse;
import soat.project.fastfoodsoat.application.output.client.AuthClientOutput;

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
