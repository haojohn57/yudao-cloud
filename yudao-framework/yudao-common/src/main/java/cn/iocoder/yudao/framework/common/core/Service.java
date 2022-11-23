package cn.iocoder.yudao.framework.common.core;

/**
 * 基础服务类接口
 *
 * @author 益莲科技
 */
public interface Service<T, D> {
    /**
     * 新增
     *
     * @param type Object
     * @return Object
     */
    T add(T type);

    /**
     * 删除
     *
     * @param id Object Id
     * @return Boolean
     */
    boolean delete(Long id);

    /**
     * 更新
     *
     * @param type Object
     * @return Object
     */
    T update(T type);

    /**
     * 通过 ID 查询
     *
     * @param id Object Id
     * @return Object
     */
    T selectById(Long id);

}
