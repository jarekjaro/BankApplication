package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.service.ClassDeclarationSpy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class TestService {
    public static boolean isEquals(Object object1, Object object2) {
        if (classesEquals(object1, object2)) {
            if (notAnnotatedFieldsEquals(object1, object2)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private static boolean classesEquals(Object object1, Object object2) {
        Class object1Class = object1.getClass();
        Class object2Class = object2.getClass();
        return object1Class == object2Class;
    }

    private static boolean notAnnotatedFieldsEquals(Object object1, Object object2) {
        Class object1Class = object1.getClass();
        Class object2Class = object2.getClass();
        Field[] fieldsFromFirstClass = object1Class.getDeclaredFields();
        Field[] fieldsFromSecondClass = object2Class.getDeclaredFields();
        setAccessibleFields(fieldsFromFirstClass);
        setAccessibleFields(fieldsFromSecondClass);
        for (int i = 0; i < fieldsFromFirstClass.length; i++) {
            if (fieldNotAnnotatedNoDB(fieldsFromFirstClass[i])) {
                try {
                    boolean fieldsEqual = fieldsFromFirstClass[i].get(object1)
                            .equals(fieldsFromSecondClass[i].get(object2));
                    if (!fieldsEqual) {
                        return false;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    private static void setAccessibleFields(Field[] fieldsFromFirstClass) {
        for (Field field : fieldsFromFirstClass) {
            field.setAccessible(true);
        }
    }

    private static boolean fieldNotAnnotatedNoDB(Field fieldOfClass) {
        Annotation[] ann = fieldOfClass.getAnnotations();
        if (ann.length != 0) {
            return false;
        } else {
            return true;
        }
    }
}
