package io.github.sergejsvisockis.docgen.mock.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocGenController {

    @PostMapping("/generateDocument")
    public ResponseEntity<?> generateDocument(@RequestBody Object value) {
        return ResponseEntity.ok().build();
    }

}
