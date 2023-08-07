package databaseentities;

import io.qameta.allure.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditEntity {
    public Status status;
}
