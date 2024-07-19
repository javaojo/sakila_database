package com.example.sakilademo.utility;

import jakarta.validation.groups.Default;

public final class ValidationGroup {

    public interface Update extends Default {}
    public interface Create extends Default {}
    public interface Delete extends Default {}
    public interface Insert extends Default {}

}
