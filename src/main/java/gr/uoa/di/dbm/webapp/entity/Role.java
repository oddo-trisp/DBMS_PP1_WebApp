package gr.uoa.di.dbm.webapp.entity;

public enum Role {
    ROLE_USER(2L),
    ROLE_ADMIN(1L);

    private Long value;

    Role(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
