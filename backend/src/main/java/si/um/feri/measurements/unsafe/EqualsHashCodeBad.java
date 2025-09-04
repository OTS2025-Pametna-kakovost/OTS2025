package si.um.feri.measurements.unsafe;

import java.util.Objects;

/**
 * equals/hashCode contract violation on purpose.
 */
public class EqualsHashCodeBad {

    private final String id;
    private final String name;

    public EqualsHashCodeBad(String id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EqualsHashCodeBad that = (EqualsHashCodeBad) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode(){
        // completely unrelated to equality
        return name == null ? 0 : name.length();
    }
}
