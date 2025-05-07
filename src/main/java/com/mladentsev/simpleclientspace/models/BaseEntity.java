package com.mladentsev.simpleclientspace.models;


import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Абстрактный базовый класс сущности, содержащий общие поля и логику для наследуемых моделей.
 *
 * <p>Все модели базы данных, унаследованные от этого класса, будут иметь стандартные поля идентификации и временные метки
 * (дата создания и дата последнего обновления).</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(of = "id")
@ToString(callSuper = true)
public abstract class BaseEntity {

    /**
     * Уникальный идентификатор сущности.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    /**
     * Дата создания сущности.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    protected LocalDateTime createdAt;

    /**
     * Дата последнего обновления сущности.
     */
    @Column(name = "updated_at", nullable = false)
    protected LocalDateTime updatedAt;

    /**
     * Получает уникальный идентификатор сущности.
     *
     * @return идентификатор сущности
     */
    public Long getId() {
        return id;
    }

    /**
     * Устанавливает уникальный идентификатор сущности.
     *
     * @param id новый идентификатор
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Получает дату создания сущности.
     *
     * @return дата создания
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Устанавливает дату создания сущности.
     *
     * @param createdAt новая дата создания
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Получает дату последнего обновления сущности.
     *
     * @return дата последнего обновления
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Устанавливает дату последнего обновления сущности.
     *
     * @param updatedAt новая дата последнего обновления
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
