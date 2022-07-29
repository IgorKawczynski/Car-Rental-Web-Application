package pl.igokaw.reservation.value_objects;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@NoArgsConstructor
@Embeddable
public class PaymentInAdvanceValidator {
    @Getter
    @Column
    private BigDecimal paymentInAdvance;

    public PaymentInAdvanceValidator(BigDecimal paymentInAdvance) {
        if(Objects.isNull(paymentInAdvance))
            throw new IllegalArgumentException("PaymentInAdvance can't be null");
        this.paymentInAdvance = paymentInAdvance;
    }

    @Override
    public String toString() {
        return paymentInAdvance.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentInAdvanceValidator that = (PaymentInAdvanceValidator) o;
        return paymentInAdvance.equals(that.paymentInAdvance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentInAdvance);
    }
}
