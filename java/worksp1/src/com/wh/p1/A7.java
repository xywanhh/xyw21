package com.wh.p1;

public class A7 {
    enum PayrollDay {
        MONDAY(PayType.WEEKDAY),
        TUESDAY(PayType.WEEKDAY),
        WEDNESDAY(PayType.WEEKDAY),
        THURSDAY(PayType.WEEKDAY),
        FRIDAY(PayType.WEEKDAY),

        SATURDAY(PayType.WEEKEND),
        SUNDAY(PayType.WEEKEND);

        private PayType payType;
        PayrollDay(PayType payType) {
            this.payType = payType;
        }
        double pay(double hoursWorked, double payRate) {
            return payType.pay(hoursWorked, payRate);
        }

        enum PayType {
            WEEKDAY {
                double overtimePay(double hrs, double rate) {
                    return hrs <= HOURS_PRE_SHIFT ? 0 :
                            (hrs - HOURS_PRE_SHIFT) * rate / 2;
                }
            },
            WEEKEND {
                double overtimePay(double hrs, double rate) {
                    return hrs * rate / 2;
                }
            };
            private static final int HOURS_PRE_SHIFT = 8;
            abstract double overtimePay(double hrs, double rate);
            double pay(double hrs, double rate) {
                double basePay = hrs * rate;
                return basePay + overtimePay(hrs, rate);
            }
        }
    }
}
