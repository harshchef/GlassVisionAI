from flask import Flask, request, jsonify
import torch
from PIL import Image
from ultralytics import YOLO

app = Flask(__name__)

# Load the YOLOv5 model (fine-tuned for window detection)
model = YOLO("yolov5s.pt")  # Replace with your trained model

@app.route('/detect', methods=['POST'])
def detect():
    if 'image' not in request.files:
        return jsonify({'error': 'No image uploaded'}), 400

    file = request.files['image']
    img = Image.open(file)

    # Perform object detection
    results = model(img)

    detections = []
    for result in results:
        for box in result.boxes:
            x1, y1, x2, y2 = map(int, box.xyxy[0])  # Bounding box coordinates
            detections.append({"x1": x1, "y1": y1, "x2": x2, "y2": y2})

    return jsonify({'detections': detections})

if __name__ == '__main__':
    app.run(debug=True, port=5000)
