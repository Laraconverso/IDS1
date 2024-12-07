
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class WalletTest {

    @Test
    void canCreateWallet() {
        new Wallet();
    }

    @Test
    void canGetCurrentBalance() {
        var wallet = new Wallet();
        assertEquals(0, wallet.getCurrentBalance());
    }

    @Test
    void canAddAmountToWallet(){
        var wallet = new Wallet();
        wallet.addCurrency(15);
    }

}
