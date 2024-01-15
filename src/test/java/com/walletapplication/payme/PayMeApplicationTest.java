package com.walletapplication.payme;

import org.junit.jupiter.api.Test;

class PayMeApplicationTest {
    @Test
    void testCorsConfigurer() {

        (new PayMeApplication()).corsConfigurer();
    }
}
