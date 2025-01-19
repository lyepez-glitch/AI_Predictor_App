//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.demo;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoveController {
    public MoveController() {
    }

    @GetMapping({"/moves"})
    public List<String> getMoves() {
        return List.of("Opponent throws a jab", "Opponent throws a roundhouse kick to the body", "Opponent attempts a clinch", "Opponent throws a low kick", "Opponent goes for a spinning back fist", "Opponent throws a front kick");
    }
}
