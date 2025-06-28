package domain.reminders.dao.filtres.generic;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CriteriaOperation {
    GREATER(">"),
    LESS("<"),
    EQUALS("=");

    private final String stringOperation;
}
