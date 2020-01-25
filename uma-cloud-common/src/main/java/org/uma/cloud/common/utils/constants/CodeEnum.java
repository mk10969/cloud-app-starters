package org.uma.platform.common.utils.constants;

import java.util.Objects;
import java.util.stream.Stream;

public interface CodeEnum<T, E extends Enum<E>> {

    T getCode();

    /**
     * [defaultメソッド] Enum型にキャストする。
     * シンプルに活用しているのが、toEnum()メソッド。サブクラスの型を扱う際に便利 =>「再帰的ジェネリクス」
     */
    @SuppressWarnings("unchecked")
    default E toEnum() {
        return (E) this;
    }

    /**
     * [staticメソッド] 指定されたCodeEnumを実装したEnumを表示順にソートしたリストを返却する
     */
//    static <T, E extends Enum<E>> List<E> getOrderedList(Class<? extends SingleValueEnum<T, E>> enumClazz) {
//        return Stream.of(enumClazz.getEnumConstants())
//                .sorted(Comparator.comparing(CodeEnum::getOrder))
//                .map(SingleValueEnum::toEnum)
//                .collect(Collectors.toList());
//    }

    /**
     * [static メソッド] 指定されたCodeから逆引きして、Enumを返却する。
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

}
