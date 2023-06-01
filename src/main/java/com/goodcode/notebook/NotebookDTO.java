package com.goodcode.notebook;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class NotebookDTO {
    final String avatar;
    final String title = "My New Notebook";
    final String description = "This is a description about my new notebook.";
    final UUID userId;
}
