package com.ivanfranchin.clientshell.config;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class ClientShellPromptProvider implements PromptProvider {

    @Override
    public AttributedString getPrompt() {
        return new AttributedString("client-shell> ", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }
}
