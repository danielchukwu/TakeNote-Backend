package com.goodcode.note;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class NoteDTO {
    final String avatar;
    final String title;
    final String description;
    final String body;
    final UUID userId;
    final UUID notebookId;
}
