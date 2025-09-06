package com.sivalabs.demo;

import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@Command
public class MyCommands {

	@Command(command = "say-hello")
	public String sayHello(@Option(defaultValue = "World") String arg) {
		return "Hello " + arg;
	}

    @Command(command = "say-goodbye")
    public String sayGoodbye(@Option(defaultValue = "World") String arg) {
        return "Goodbye " + arg;
    }
}