package team.kelly.kellyserver.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Table(name = "refresh_token")
@Entity
public class RefreshToken {

    @Id
    @Column(name = "username", length = 50)
    private String username;

    @Column(name = "value", length = 200)
    private String value;

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }

    @Builder
    public RefreshToken(String key, String value) {
        this.username = key;
        this.value = value;
    }
}