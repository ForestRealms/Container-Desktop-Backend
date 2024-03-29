package container.desktop.containerdesktopbackend.event;

import container.desktop.api.entity.Container;
import container.desktop.api.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class ContainerCreatedEvent extends ApplicationEvent {
    private final Container container;
    private final User operator;
    public ContainerCreatedEvent(Object source, Container container, User operator) {
        super(source);
        this.container = container;
        this.operator = operator;
    }
}
