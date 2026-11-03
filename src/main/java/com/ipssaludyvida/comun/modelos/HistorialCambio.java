package com.ipssaludyvida.comun.modelos;

import com.ipssaludyvida.hu001.modelos.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Un cambio de un dato de un registro: el valor anterior, el nuevo, si fue actualización o
 * corrección, quién y cuándo (RC-5, RC-6). Es la tabla historial_cambio del modelo E-R, común al
 * paciente (HU-006) y, cuando lleguen, a la atención, la oposición y la voluntad anticipada. El
 * dato anterior no se borra: queda aquí. Nada se borra de esta tabla.
 */
@Entity
@Getter
@Setter
public class HistorialCambio {

    /** Los dos casos de RC-6: el dato cambió en la realidad, o se registró mal. */
    public enum Tipo {
        ACTUALIZACION("Actualización"), CORRECCION("Corrección");

        private final String texto;

        Tipo(String texto) {
            this.texto = texto;
        }

        public String texto() {
            return texto;
        }
    }

    @Id
    @GeneratedValue
    private Long id;

    /** La tabla del registro, como "paciente". */
    @Column(nullable = false, length = 20)
    private String tabla;

    /**
     * El número del registro que cambió. No es una llave foránea, porque apunta a varias tablas; la
     * valida el sistema (modelo E-R, "Reglas que el modelo tiene que cumplir").
     */
    @Column(nullable = false)
    private Long registroId;

    /** El dato que cambió, como "MUNICIPIO_RESIDENCIA". */
    @Column(nullable = false, length = 40)
    private String dato;

    @Column(length = 400)
    private String valorAnterior;

    @Column(length = 400)
    private String valorNuevo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 13)
    private Tipo tipo;

    private Long usuarioId;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    // La llave foránea de usuarioId hacia quien hizo el cambio; solo lectura.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioId", insertable = false, updatable = false)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Usuario usuario;
}
