package com.zodiac.entity;

/**
 * Created by Immortan on 3/19/2016.
 */
public interface Killable {

    void hit(Projectile projectile);
    void die();
    
}
