package com.whisperspace.Whisperspace_backend.conversation.dto;
import com.whisperspace.Whisperspace_backend.auth.entity.user;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewConvoreq {
    private String name;
//    private List<Long> participantIds;
    private List<String> participants;
}
