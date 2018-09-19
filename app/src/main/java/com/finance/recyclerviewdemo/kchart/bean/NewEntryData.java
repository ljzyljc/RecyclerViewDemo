package com.finance.recyclerviewdemo.kchart.bean;

import java.util.LinkedList;

/**
 * Created by Jackie on 2018/9/19.
 */
public class NewEntryData {
    /**
     * the direction to add an entry.
     */
    public static final int HEAD = 0;

    /**
     * the direction to add an entry.
     */
    public static final int TAIL = 1;

    /**
     * A entry set,can add or remove from both side(head&tail).
     */
    public LinkedList<NewEntry> entries = new LinkedList<>();

    /**
     * maximum y-value in the y-value array
     */
    public float mYMax = 0.0f;

    /**
     * the minimum y-value in the y-value array
     */
    public float mYMin = 0.0f;

    /**
     * the maximum y-value of volume
     */
    public float mMaxYVolume;

    /**
     * Add an entry
     */
    public void addEntry(NewEntry entry) {
        addEntry(entry, TAIL);
    }

    /**
     * Add an entry to the given side.
     *
     * @param side must one of {@link NewEntryData#HEAD}、{@link NewEntryData#TAIL},
     *             default is TAIL.
     */
    public void addEntry(NewEntry entry, int side) {
        if (side != HEAD && side != TAIL) {
            side = TAIL;
        }
        if (side == HEAD) {
            entries.addFirst(entry);
        } else {
            entries.addLast(entry);
        }
    }

    /**
     * Calc the max and min value int the special range.
     */
    public void calcMinMax(int start, int end) {
        int endValue;
        if (end == 0 || end >= entries.size())
            endValue = entries.size() - 1;
        else
            endValue = end;

        mYMin = Float.MAX_VALUE;
        mYMax = -Float.MAX_VALUE;
        mMaxYVolume = -Float.MAX_VALUE;

        for (int i = start; i <= endValue; i++) {
            NewEntry entry = entries.get(i);

            if (entry.money < mYMin)
                mYMin = entry.money;

            if (entry.money > mYMax)
                mYMax = entry.money;

            if (entry.money > mMaxYVolume)
                mMaxYVolume = entry.money;
        }
    }
}
