/**
 *  mannequin
 *
 *  Fake device type for trying things on
 *
 *  Copyright 2015 Kevin Shuk
 *
 */
import groovy.transform.Field
import org.codehaus.groovy.runtime.StackTraceUtils

import physicalgraph.device.HubAction
import physicalgraph.zwave.Command

// Constants
//

// time constants
@Field final Integer SECOND_MSEC = 1000
@Field final Integer MINUTE_SEC = 60
@Field final Integer MINUTE_MSEC = MINUTE_SEC * SECOND_MSEC
@Field final Integer HOUR_SEC = 60 * MINUTE_SEC
@Field final Integer HOUR_MSEC = HOUR_SEC * SECOND_MSEC

@Field final Integer DEFAULT_INTERCMD_DELAY_MSEC = SECOND_MSEC * 2

@Field final Integer PREF_DEFAULT_WAKE_UP_INTERVAL_HR = 4
@Field final Integer DEFAULT_WAKE_UP_INTERVAL_SEC = PREF_DEFAULT_WAKE_UP_INTERVAL_HR * HOUR_SEC
@Field final Integer MIN_WAKE_UP_INTERVAL_SEC = 10 * MINUTE_SEC
@Field final Integer MAX_WAKE_UP_INTERVAL_SEC = 24 * HOUR_SEC

@Field final Integer ASSOC_CHECK_INTERVAL_MSEC = 24 * HOUR_MSEC // 24 hours
@Field final Short   ASSOCIATION_GROUP_ID = 1

@Field final Integer PREF_DEFAULT_MOTION_TIMEOUT_MIN = 3
@Field final Integer MIN_MOTION_TIMEOUT_MIN = 1
@Field final Integer MAX_MOTION_TIMEOUT_MIN = 255

@Field final Integer TEMP_SENSOR_FILTER_HISTORY_SIZE = 2

// tamper handling
@Field final String  PREF_DEFAULT_TAMPER_FALSE_ON_WAKE = true

@Field Map tamper_attr_map = [:]
tamper_attr_map.NAME = 'tamper'
tamper_attr_map.TRUE = 'detected'
tamper_attr_map.FALSE = 'clear'
tamper_attr_map.FALSE_INIT = 'device initialization'
tamper_attr_map.FALSE_AUTO = 'Automatically'
tamper_attr_map.FALSE_MANUAL = 'Manually'
@Field final Map TAMPER = tamper_attr_map

// binary sensor values and interpretations
@Field final Short ZWAVE_TRUE  = 0xFF
@Field final Short ZWAVE_FALSE = 0x00
@Field final Map   ZWAVE = [ZWAVE_TRUE: true, ZWAVE_FALSE: false].withDefault {false}

@Field Map motion_attr_map = [:]
motion_attr_map.NAME = 'motion'
motion_attr_map.LABEL = motion_attr_map.NAME
motion_attr_map.TRUE = 'active'
motion_attr_map.FALSE = 'inactive'
motion_attr_map.STATEMAP = [(true): motion_attr_map.TRUE, (false): motion_attr_map.FALSE].withDefault {motion_attr_map.FALSE}
@Field final Map MOTION = motion_attr_map

@Field final String TEMPERATURE_ATTR_NAME = 'temperature'
@Field final String TEMPERATURE_ATTR_LABEL = TEMPERATURE_ATTR_NAME

@Field final Map    MAIN_ATTR = MOTION

@Field final Map CMD_CLASS_VERSIONS = [0x20: 1, 0x30: 1, 0x31: 4, 0x71: 2, 0x72: 1, 0x80: 1, 0x84: 2, 0x85: 1, 0x86: 1]

// tile colors
@Field final String COLOR_DKRED  = '#C92424'
@Field final String COLOR_RED    = '#FF0033'
@Field final String COLOR_ORANGE = '#FFA81E'
@Field final String COLOR_YELLOW = '#FFFF00'
@Field final String COLOR_GREEN  = '#44b621'
@Field final String COLOR_CYAN   = '#1EE3FF'
@Field final String COLOR_DKBLUE = '#153591'
@Field final String COLOR_WHITE  = '#FFFFFF'

@Field final String COLOR_BATT_FULL  = COLOR_GREEN
@Field final String COLOR_BATT_GOOD  = COLOR_GREEN
@Field final String COLOR_BATT_OK    = COLOR_YELLOW
@Field final String COLOR_BATT_LOW   = COLOR_RED
@Field final String COLOR_BATT_CRIT  = COLOR_RED

@Field final String COLOR_TMP_COLDER = '#153591'
@Field final String COLOR_TMP_COLD   = '#1E9CBB'
@Field final String COLOR_TMP_COOL   = '#90D2A7'
@Field final String COLOR_TMP_ROOM   =  COLOR_GREEN
@Field final String COLOR_TMP_WARM   = '#F1D801'
@Field final String COLOR_TMP_HOT    = '#D04E00'
@Field final String COLOR_TMP_HOTTER = '#BC2323'

// smartlog scopes

@Field final String ZWEH = 'Z-WaveEventHandler' // For handlers of events sent by the device itself
@Field final String DTI = 'DeviceTypeInternal' // for commands that are automatically called in a device type's lifecycle
@Field final String CCMD = 'STDeviceCommand' // capability or standalone command
@Field final String CCC = 'CommandClassCommand' // wraps a single command class

@Field def smartlog
@Field Long wakeUpPeriod

@Field final Boolean DEBUG_MODE = false

preferences
{
	input(name: "motionTimeout", type: "enum", title: "Motion timeout minutes",
		options: ['1','2','3','4','5'],
		defaultValue: PREF_DEFAULT_MOTION_TIMEOUT_MIN, required: true,
		description: "$PREF_DEFAULT_MOTION_TIMEOUT_MIN")

	input(name: "wakeupIntervalHrs", type: "enum", title: "Hours between wakeups (1-24)",
		options: ['1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24'],
		defaultValue: PREF_DEFAULT_WAKE_UP_INTERVAL_HR, required: true,
		description: "$PREF_DEFAULT_WAKE_UP_INTERVAL_HR")

	input(name: "tamperClearAuto", type: "boolean", title: "Clear tamper alerts automatically?",
		description: 'Indicate if tamper alerts clear automatically upon wake or state change after the device cover is closed.',
		defaultValue: PREF_DEFAULT_TAMPER_FALSE_ON_WAKE)

	input(name: "wakeupDevFlag", type: "boolean", title: "Development mode",
		description: "Set Wake Up Interval to 10 minutes for testing")
}

metadata {
	definition (name: "mannequin", namespace: "surfous", author: "surfous") {
		capability "Test Capability"
	}

	simulator {
		// TODO: define status and reply messages here
	}

	tiles {
		// TODO: define your main and details tiles here
	}
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"

}

