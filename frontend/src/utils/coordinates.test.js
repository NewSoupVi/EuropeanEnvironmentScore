import { describe, it, expect } from 'vitest';
import { validateCoordinates } from './coordinates';

describe('validateCoordinates', () => {
  it('should accept valid coordinates', () => {
    expect(validateCoordinates('40.7128', '-74.0060')).toBe(true);
    expect(validateCoordinates('0', '0')).toBe(true);
    expect(validateCoordinates('-90', '180')).toBe(true);
    expect(validateCoordinates('90', '-180')).toBe(true);
  });

  it('should reject coordinates outside valid range', () => {
    expect(validateCoordinates('91', '0')).toBe(false);
    expect(validateCoordinates('-91', '0')).toBe(false);
    expect(validateCoordinates('0', '181')).toBe(false);
    expect(validateCoordinates('0', '-181')).toBe(false);
  });

  it('should reject garbage inputs without crashing', () => {
    expect(validateCoordinates('abc', 'def')).toBe(false);
    expect(validateCoordinates('not a number', '123')).toBe(false);
    expect(validateCoordinates('45.5', 'xyz')).toBe(false);
    expect(validateCoordinates('', '')).toBe(false);
    expect(validateCoordinates(null, null)).toBe(false);
    expect(validateCoordinates(undefined, undefined)).toBe(false);
  });
});

