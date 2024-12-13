package com.example.demo.characters;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class BossTest extends ApplicationTest {

    @Test
    void testShieldImage(){
        Boss boss = new Boss();
        assertNotNull(boss.getShieldImage());
    }
}
