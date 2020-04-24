package org.uma.cloud.common.utils.constants;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface CodeEnum<T, E extends Enum<E>> {

    T getCode();

    String getCodeName();

    /**
     * [defaultメソッド] Enum型にキャストする。
     * シンプルに活用しているのが、toEnum()メソッド。サブクラスの型を扱う際に便利 =>「再帰的ジェネリクス」
     */
    @SuppressWarnings("unchecked")
    default E toEnum() {
        return (E) this;
    }

    /**
     * Enumを表示順にソートしたリストを返却する。
     */
    static <T, E extends Enum<E>> List<E> getOrderedList(Class<? extends CodeEnum<T, E>> enumClazz) {
        return Stream.of(enumClazz.getEnumConstants())
                .sorted()
                .map(CodeEnum::toEnum)
                .collect(Collectors.toList());
    }

    /**
     * Codeから逆引きして、Enumを返却する。
     * JvLinkから返却されたデータを、デシリアライズするときに利用する。
     */
    static <T, E extends Enum<E>> E reversibleFindOne(T code, Class<? extends CodeEnum<T, E>> enumClazz) {
        return Stream.of(enumClazz.getEnumConstants())
                .filter(enums -> Objects.equals(enums.getCode(), code))
                .findFirst()
                .map(CodeEnum::toEnum)
                .orElseThrow(() -> new IllegalArgumentException(
                        "[引数のコード :" + code + "] " + enumClazz + " に引数のコードが、存在しません。"
                ));
    }

    /**
     * Code名から逆引きして、Enumを返却する。
     * Jpaで利用する。DBには日本語で保存しておく。
     */
    static <T, E extends Enum<E>> E convertOf(String codeName, Class<? extends CodeEnum<T, E>> enumClazz) {
        return Stream.of(enumClazz.getEnumConstants())
                .filter(enums -> Objects.equals(enums.getCodeName(), codeName))
                .findFirst()
                .map(CodeEnum::toEnum)
                .orElseThrow(() -> new IllegalArgumentException(
                        "[引数のコード名 :" + codeName + "] " + enumClazz + " に引数のコード名が、存在しません。"
                ));
    }

}
