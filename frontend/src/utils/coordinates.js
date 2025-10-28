export const validateCoordinates = (lat, lng) => {
  if (!lat || !lng) {
    return false;
  }

  const latNum = parseFloat(lat);
  const lngNum = parseFloat(lng);

  return !isNaN(latNum) && !isNaN(lngNum) && latNum >= -90 && latNum <= 90 && lngNum >= -180 && lngNum <= 180;
};

